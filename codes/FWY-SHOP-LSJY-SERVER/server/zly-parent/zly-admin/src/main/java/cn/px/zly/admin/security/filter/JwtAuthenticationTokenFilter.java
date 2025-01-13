package cn.px.zly.admin.security.filter;

import cn.px.zly.admin.security.vo.SPSecurityUser;
import cn.px.zly.admin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @Description token过滤器来验证token有效性
 * @date 2024-08
 * @Param
 * @return
 **/
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();
//        log.warn("uri={} invoke JwtAuthenticationTokenFilter", uri);
        SPSecurityUser securityUser = jwtUtil.getUserFromToken(request);
        if (null != securityUser) {
            Set<String> permissions = Collections.EMPTY_SET;
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        if(!uri.contains("/zly")){
            return true;
        }
        return super.shouldNotFilter(request);
    }
}
