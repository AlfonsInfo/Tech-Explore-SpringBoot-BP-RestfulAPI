package belajar.springboot._04Rest.restspring.service;

import belajar.springboot._04Rest.restspring.dto.ApiResponse;
import belajar.springboot._04Rest.restspring.dto.ErrorSchema;
import belajar.springboot._04Rest.restspring.dto.request.CreateCollegeStudentRequest;
import belajar.springboot._04Rest.restspring.dto.request.ReadCollegeStudentRequest;
import belajar.springboot._04Rest.restspring.dto.request.UpdateCollegeStudentRequest;
import belajar.springboot._04Rest.restspring.dto.response.CreateCollegeStudentResponse;
import belajar.springboot._04Rest.restspring.dto.response.ReadCollegeStudentResponse;
import belajar.springboot._04Rest.restspring.entity.CollegeStudent.CollegeStudent;
import belajar.springboot._04Rest.restspring.repository.CollegeStudentRepository;
import belajar.springboot._04Rest.restspring.util.logging.LogHelper;
import belajar.springboot._04Rest.restspring.util.validation.ValidationService;
import belajar.util.config.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class CollegeStudentService {

    @Autowired
    private CollegeStudentRepository collegeStudentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private LogHelper logHelper;


    @Autowired
    private PathConfig pathConfig;




    @Transactional
    public ApiResponse<CreateCollegeStudentResponse> createCollegeStudent(CreateCollegeStudentRequest request)
    {
        String activity = "CREATE COLLEGE STUDENT";
        log.info(logHelper.logTemplate(pathConfig.getCurrentMethodName(),activity));
        validationService.validate(request);

        try{
            log.info(logHelper.logTemplate(pathConfig.getCurrentMethodName(),"START CREATING & SAVING collegeStudent OBJECT","{}"));
            CollegeStudent collegeStudent = CollegeStudent
                .builder()
                .namaDepan(request.getNamaDepan().toUpperCase())
                .namaBelakang(request.getNamaBelakang().toUpperCase())
                .namaLengkap(request.getNamaDepan().toUpperCase() +" "+
                        (Objects.nonNull(request.getNamaBelakang()) ?
                                request.getNamaBelakang().toUpperCase() :
                                request.getNamaDepan().toUpperCase())
                )
                .angkatan(request.getAngkatan())
                .jurusan(request.getJurusan().toUpperCase())
                .build();
            collegeStudentRepository.save(collegeStudent);
            log.info(pathConfig.getCurrentMethodName(),"FINISH CREATING & SAVING collegeStudent OBJECT".toUpperCase(),collegeStudent);


            ErrorSchema errorSchema = new ErrorSchema();
            errorSchema.setSuccessResponse("Sukses Menambahkan data mahasiswa","Success Adding College Student Data");
            CreateCollegeStudentResponse response = modelMapper.map(collegeStudent, CreateCollegeStudentResponse.class);
            log.info(logHelper.logTemplateEndService(pathConfig.getCurrentMethodName(),activity,true));
            return ApiResponse.<CreateCollegeStudentResponse>builder().responseData(response).errorSchema(errorSchema).build();
        } catch (Exception e){
            log.info("Exception : " + e.getMessage());
            log.info(logHelper.logTemplateEndService(pathConfig.getCurrentMethodName(),activity,false));
            return null;
        }

    }

    public ApiResponse<ReadCollegeStudentResponse> readCollegeStudent(ReadCollegeStudentRequest request)
    {
        String activity = "READ DATA COLLEGE STUDENT";
        log.info(logHelper.logTemplate(pathConfig.getCurrentMethodName(),activity,"data request" + request.getNamaDepan()));
        try{
            ReadCollegeStudentResponse responseData  = new ReadCollegeStudentResponse();

            List<CollegeStudent> tempListCollegeStudent =
                    collegeStudentRepository.getFiltered(
                        request.getNomorIndukMahasiswa(),
                        request.getNamaDepan(),
                        request.getNamaBelakang(),
                        request.getJurusan(),
                        request.getAngkatan()
                    );

            responseData.setData(tempListCollegeStudent);
            ErrorSchema errorSchema = new ErrorSchema();
            errorSchema.setSuccessResponse("Sukses Membaca Data", "Success Read Data");
            return ApiResponse.<ReadCollegeStudentResponse>builder()
                    .errorSchema(errorSchema)
                    .responseData(responseData)
                    .build();
        }catch (Exception e){
            log.info("Exception : " + e.getMessage());
            return null;
        }
    }

    public ApiResponse<CollegeStudent> updateCollegeStudent(String npm, UpdateCollegeStudentRequest request){
        try{
            Optional<CollegeStudent> collegeStudentOpt = collegeStudentRepository.findById(npm);
            CollegeStudent collegeStudent = null;
            if(collegeStudentOpt.isPresent()){
                collegeStudent =  collegeStudentOpt.get();
                collegeStudent.setNamaDepan(request.getNamaDepan());
                collegeStudent.setNamaBelakang(request.getNamaLengkap());
                collegeStudent.setJurusan(request.getJurusan());
                collegeStudent.setAngkatan(request.getAngkatan());
                collegeStudentRepository.save(collegeStudent);
            }else{
                //todo throw exception
            }

            ErrorSchema errorSchema = new ErrorSchema();
            errorSchema.setSuccessResponse("Sukses Mengubah Data", "Success Update Data");
            return ApiResponse.<CollegeStudent>builder()
                    .responseData(collegeStudent)
                    .errorSchema(errorSchema)
                    .build();
        }catch (Exception e)
        {
            log.info("Exception : " + e.getMessage());
            return null;
        }
    }


}
