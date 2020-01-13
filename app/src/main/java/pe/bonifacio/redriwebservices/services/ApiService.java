package pe.bonifacio.redriwebservices.services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @GET("/proyectos")
    Call<List<Proyecto>> getProyectos();

    @FormUrlEncoded
    @POST("/proyectos")
    Call<Proyecto> createProyecto(@Field("nombre") String nombre);
    @Multipart
    @POST("/proyectos")
    Call<Proyecto> createProyecto(@Part("nombre") RequestBody nombre,
                                  @Part MultipartBody.Part imagen
    );
    @DELETE("/proyectos/{id}")
    Call<String> destroyProyecto(@Path("id") Long id);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Usuario> login(@Field("correo") String correo,
                        @Field("password") String password);


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

}
