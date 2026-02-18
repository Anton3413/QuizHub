package com.anton3413.quiz_hub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Если заголовка нет или он не начинается с "Bearer ", пропускаем запрос дальше
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Вырезаем сам токен из строки (после слова "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Достаем имя пользователя из токена через наш JwtService
        username = jwtService.extractUsername(jwt);

        // 5. Если имя есть, а пользователь в системе еще не авторизован (в рамках этого запроса)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Загружаем пользователя из базы данных
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. Проверяем, валиден ли токен (не протух ли он и принадлежит ли этому юзеру)
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Создаем объект "авторизованного пользователя"
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Добавляем детали запроса (IP и т.д.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. САМЫЙ ВАЖНЫЙ МОМЕНТ: Кладем пользователя в контекст Spring Security
                // Теперь Спринг знает, кто делает запрос, до самого конца его выполнения
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Передаем эстафету следующему фильтру в цепочке
        filterChain.doFilter(request, response);
    }
}