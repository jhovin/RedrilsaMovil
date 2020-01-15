package pe.bonifacio.redriwebservices.services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pe.bonifacio.redriwebservices.models.MaquinaIm;
import pe.bonifacio.redriwebservices.models.MaquinaSup;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://10.0.2.2:8088";

    //Proyectos
    @GET("/proyectos")
    Call<List<Proyecto>> getProyectos();

    @FormUrlEncoded
    @POST("/proyectos")
    Call<Proyecto> createProyecto(@Field("nombre") String nombre,@Field("cliente")String cliente,
                                  @Field("distrito") String distrito,@Field("provincia")String provincia,
                                  @Field("departamento")String departamento,@Field("gerente")String gerente,
                                  @Field("telefono")String telefono);
    @Multipart
    @POST("/proyectos")
    Call<Proyecto> createProyecto(@Part("nombre") RequestBody nombre,@Part("cliente") RequestBody cliente,
                                  @Part("distrito") RequestBody distrito,@Part("provincia") RequestBody provincia,
                                  @Part("departamento")RequestBody departamento,@Part("gerente")RequestBody gerente,
                                  @Part("telefono")RequestBody telefono,
                                  @Part MultipartBody.Part imagen);

    @GET("/proyectos/{id}")
    Call<Proyecto> showProyecto(@Path("id") Long id);

    @DELETE("/proyectos/{id}")
    Call<String> destroyProyecto(@Path("id") Long id);

    @GET("/proyectos/{id}")
    Call<Proyecto> showMascotaQR(@Path("id") Long id);


    //Login
    @FormUrlEncoded
    @POST("/auth/login")
    Call<Usuario> login(@Field("correo") String correo,
                        @Field("password") String password);
    //Usuarios
    @GET("/usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("/usuarios/{id}")
    Call<Usuario> showUsuario(@Path("id") Long id);

    @FormUrlEncoded
    @POST("/usuarios")
    Call<Usuario> createUsuario(@Field("dni") String dni,
                                @Field("nombre") String nombre,
                                @Field("cargo") String cargo,
                                @Field("correo") String correo,
                                @Field("password") String password);






    @GET("/maquinas/superficie")
    Call<List<MaquinaSup>> getMaquinasSup();

    @GET("//maquinas/superficie/{id}")
    Call<MaquinaSup> showMaquinasSup(@Path("id") Long id);

    @FormUrlEncoded
    @POST("//maquinas/superficie")
    Call<MaquinaSup> createMaquinasSup(@Field("nombre_sup") String nombre_sup,
                                @Field("observacion") String observacion);


    @GET("/maquinas/mina")
    Call<List<MaquinaIm>> getMaquinasIm();

    @GET("//maquinas/mina/{id}")
    Call<MaquinaIm> showMaquinasIm(@Path("id") Long id);

    @FormUrlEncoded
    @POST("//maquinas/mina")
    Call<MaquinaIm> createMaquinasIm(@Field("nombre_im") String nombre_im,
                                       @Field("observacion") String observacion);

}
