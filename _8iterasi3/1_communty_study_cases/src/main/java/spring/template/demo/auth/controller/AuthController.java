package spring.template.demo.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.template.demo.auth.dto.request.LoginRequest;
import spring.template.demo.auth.dto.request.RegisterRequest;
import spring.template.demo.auth.dto.response.LoginResponse;
import spring.template.demo.auth.service.AuthService;
import spring.template.demo.entities.constant.Constant;
import spring.template.demo.entities.dto.ApiResponse;
import spring.template.demo.entities.dto.BaseResponse;

@RequestMapping(path = Constant.EndPoint.USER_PREFIX) // ** prefix endpoints with api */
@RestController
public class AuthController{

    private final AuthService authService;

    @Autowired
    public AuthController (AuthService authService){

        this.authService = authService;
    }


    @PostMapping(value = Constant.EndPoint.REGISTER_PATH,
    consumes = {"application/json"})
    public BaseResponse register(@RequestBody RegisterRequest request, HttpServletRequest httpServletRequest)
    {
        //todo : uncomment comment below for enabled captcha when service ready
        //String response = httpServletRequest.getParameter("g-recaptcha-response");
        //captchaService.processRecaptcha(response);
        return authService.register(request);
    }


    @PostMapping(value =  Constant.EndPoint.LOGIN_PATH)
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

}


PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO nama_tabel (kolom1, kolom2) VALUES (?, ?)");
preparedStatement.setString(1, "value 1");
preparedStatement.setInt(2, "value 2");
int affectedRows = preparedStatement.executeUpdate();

CallableStatement callableStatement = connection.prepareCall("{call nama_prosedur(?, ?)}");
callableStatement.setString(1, parameter1);
callableStatement.setInt(2, parameter2);
ResultSet resultSet = callableStatement.executeQuery();


public void registerDriver(){
    Driver postgreDriver = new Driver();
    try {
        DriverManager.registerDriver(postgreDriver);
    } catch (SQLException e) {
        System.out.println(e);
    }
}
