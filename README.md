# SeoulTour
**< 스프링 부트를 사용한 서울 여행지 및 숙소 추천 사이트 >** <br>

회원가입 페이지에서는 사용자 정보를 안전하게 입력받고 서버에 전송하여, <br>
새로운 사용자를 데이터베이스에 저장합니다. <br>
여기서는 회원가입 페이지의 구성 요소와 주요 기능, 그리고 구현 방식을 설명합니다. <br>
<br>

## 회원가입 페이지 구성요소
입력필드 : 
- 이름 input
- 아이디 input
- 비밀번호 input
- 비밀번호 확인 input
- 이메일 input

버튼 : 회원가입 완료 (로그인 페이지로 이동)
<br><br>

## 회원가입 페이지 Preview
![s](https://github.com/Johyeonna/SeoulTour/assets/163944887/4af09e3e-3165-46b3-9ba1-62017b9b225d)
![화면 캡처 2024-06-03 114411](https://github.com/Johyeonna/SeoulTour/assets/163944887/2083e750-f1e9-4b3b-b260-ae241ab18142)
![ezgif com-video-to-apng-converter](https://github.com/Johyeonna/SeoulTour/assets/163944887/8ab8368c-9d72-470e-b214-f4134c0ca74f)
<br><br>

## 코드 설명
```java
@Controller
@RequiredArgsConstructor
public class RegisterLoginLogoutController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setName(memberRequestDto.getName());
        member.setUserid(memberRequestDto.getUserid());
        member.setPassword(memberRequestDto.getPassword());
        member.setEmail(memberRequestDto.getEmail());
        member.setAuthType(AuthType.USER);

        Map<String, String> response = new HashMap<>();
        if (memberService.hasUserId(member.getUserid())) {
            response.put("status", "error");
            response.put("message", "아이디가 이미 존재합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            Member registerdMember = memberService.createMember(member);
            if (registerdMember != null) {
                response.put("status", "success");
                response.put("message", "가입 성공");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "가입 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }


        }
    }
}
```
<br>

### @Controller
이 어노테이션은 이 클래스가 Spring MVC 컨트롤러임을 나타냅니다. <br>
컨트롤러는 HTTP 요청을 처리하고 적절한 뷰를 반환하거나 데이터를 응답으로 전송하는 역할을 합니다. <br>

### @RequiredArgsConstructor
이 어노테이션은 Lombok 라이브러리에서 제공하는 것으로, final 필드나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성합니다. <br>
이 클래스에서는 memberService 필드에 대해 생성자를 생성합니다. <br>

### @GetMapping("/login")
이 메서드는 사용자가 로그인 페이지에 접근할 때 호출됩니다. <br>
/login URL로 GET 요청이 들어오면 login 뷰를 반환합니다. <br>
이 뷰는 로그인 페이지를 렌더링합니다. <br>

### @PostMapping("/login")
이 메서드는 회원가입 요청을 처리합니다. <br>
/login URL로 POST 요청이 들어오면, 요청 본문에 포함된 MemberRequestDto 객체를 사용하여 회원가입을 처리합니다. <br>

### 회원 정보 설정
MemberRequestDto 객체로부터 회원 정보를 가져와 Member 객체에 설정합니다.
AuthType.USER로 기본 권한 유형을 설정합니다. <br>

### 아이디 중복 확인
memberService.hasUserId(member.getUserid()) 메서드를 호출하여, 아이디가 이미 존재하는지 확인합니다.
아이디가 이미 존재하면, 오류 메시지를 포함한 응답을 반환합니다. <br>

### 회원가입 처리
memberService.createMember(member) 메서드를 호출하여 새로운 회원을 생성합니다. <br>
회원가입이 성공하면, 성공 메시지를 포함한 응답을 반환합니다. <br>
회원가입이 실패하면, 실패 메시지를 포함한 응답을 반환합니다. <br><br>

```java
@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    // 스프링 시큐리티 기능 비 활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/static/**"))
                .requestMatchers(new AntPathRequestMatcher("/**"))
        );
    }
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                 new AntPathRequestMatcher("/**")
////                                new AntPathRequestMatcher("/user/signin"),
////                                new AntPathRequestMatcher("/user/signup"),
////                                new AntPathRequestMatcher("/user/logout")
//                        ).permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(formlogin-> formlogin
//                        .loginPage("/user/signin")
//                        .defaultSuccessUrl("/board/list")
//                )
//                .logout(logout->logout
//                        .logoutSuccessUrl("/user/signin")
//                        .invalidateHttpSession(true)
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }
    // 암호화 관련 Bean등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
```
<br>

### @Configuration
이 어노테이션은 이 클래스가 Java 기반의 설정 클래스임을 나타냅니다. <br>
Spring 애플리케이션 컨텍스트에 이 클래스를 로드하여 Bean을 구성할 수 있습니다. <br>

### @Bean
이 어노테이션은 해당 메서드가 Spring Bean을 생성하고 구성하는 메서드임을 나타냅니다.

### configure()
이 메서드는 WebSecurityCustomizer 타입의 Bean을 생성합니다. <br>
이 Bean은 Spring Security의 기능을 비활성화하고 모든 요청에 대해 인증을 무시하도록 설정합니다. <br>
즉, 모든 요청에 대해 보안을 적용하지 않습니다. <br>

### bCryptPasswordEncoder()
이 메서드는 BCryptPasswordEncoder를 생성하는 Bean을 등록합니다. <br>
이것은 암호를 안전하게 저장하고 검증하기 위해 사용됩니다. <br>

### 주석 처리된 코드
주석 처리된 코드는 Spring Security의 기능을 활성화하고, 특정 URL 패턴에 대한 접근을 제한하고자 할 때 사용됩니다. <br>
이를 통해 인증, 인가, 로그인 페이지 설정 등 Spring Security의 다양한 기능을 사용할 수 있습니다. <br>
주석 처리된 코드의 주석을 해제하고 필요한 설정을 추가하여 Spring Security를 구성할 수 있습니다. <br><br>

```java
@Component
@RequiredArgsConstructor
public class MemberDao{
    private final MemberRepository memberRepository;
    public Member createMember(Member member){
        return memberRepository.save(member);
    }
    public Member findByUserId(String userid){
        Optional<Member> findedMember =  memberRepository.findByUserid(userid);
        if(findedMember.isPresent())
            return findedMember.get();
        else
            return null;
    }
}
```
<br>

### @Component
이 어노테이션은 스프링의 구성 요소로서 해당 클래스가 스프링 빈으로 등록되어야 함을 나타냅니다. <br>
즉, 스프링이 애플리케이션 컨텍스트에서 이 클래스의 인스턴스를 관리합니다. <br>

### createMember(Member member)
회원을 생성하는 메서드입니다. Member 객체를 전달받아서 memberRepository를 통해 데이터베이스에 저장합니다. <br>
memberRepository.save(member)를 호출하여 회원 객체를 데이터베이스에 저장하고, 저장된 회원 객체를 반환합니다. <br>

### findByUserId(String userid)
주어진 아이디에 해당하는 회원을 조회하는 메서드입니다. <br>
memberRepository.findByUserid(userid)를 호출하여 아이디에 해당하는 회원을 데이터베이스에서 찾습니다. <br>
Optional을 사용하여 데이터베이스에서 회원을 찾을 수 없는 경우를 처리합니다. <br>
Optional은 값이 존재하지 않을 수 있는 상황에서 null 대신 사용되며, isPresent() 메서드를 사용하여 값이 존재하는지 확인할 수 있습니다. <br><br>

```java
@Getter
@Setter
@ToString
public class MemberRequestDto {
    private Long id;
    private String name;
    private String userid;
    private String password;
    private String email;
    private AuthType authType;
}
```
<br>

### @Getter <br>
Lombok 라이브러리에서 제공하는 어노테이션으로, 모든 필드에 대한 getter 메서드를 자동으로 생성합니다. <br>

### @Setter <br>
Lombok 라이브러리에서 제공하는 어노테이션으로, 모든 필드에 대한 setter 메서드를 자동으로 생성합니다. <br>

### @ToString <br>
Lombok 라이브러리에서 제공하는 어노테이션으로, 클래스의 toString() 메서드를 자동으로 생성하여 객체의 내용을 문자열로 표시합니다. <br>




