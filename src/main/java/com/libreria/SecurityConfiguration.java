package com.libreria;
import com.libreria.servicios.ClienteServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public ClienteServicios usuarioServicio;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioServicio).
		passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()

				.antMatchers("/css/*", "/js/*", "/img/*", "/**","/main/**","/usuario/**","/actividad/**","/login/**", "/glosario/**").permitAll()
				.and().formLogin()
					.loginPage("/login")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("mail")
						.passwordParameter("contrasenia1")
						.defaultSuccessUrl("/index")
						.failureUrl("/login?error=error")
						.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout=logout")
					.permitAll()
				.and().csrf()
					.disable();
	}
	

}
