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


