package com.toodari.beansbox.security.service;

import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.repository.MemberRepository;
import com.toodari.beansbox.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername:" + username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if(result.isEmpty()) {
            throw new UsernameNotFoundException("username not found...");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getMname(),
                        member.getMphone(),
                        member.getMyear(),
                        member.getMmonth(),
                        member.getMday(),
                        member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())

                );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);
        return memberSecurityDTO;
    }
}
