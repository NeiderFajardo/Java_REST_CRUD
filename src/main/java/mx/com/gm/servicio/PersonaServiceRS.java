package mx.com.gm.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import mx.com.gm.data.PersonaDao;
import mx.com.gm.domain.Persona;

/**
 *
 * @author neideralejandro
 */
@Stateless
@Path("/personas")
public class PersonaServiceRS {
    @Inject
    private PersonaDao personaDao;

    @GET
    @Produces(value=MediaType.APPLICATION_JSON)
    public List<Persona> listarPersonas(){
        List personas = personaDao.encontrarTodasPersonas();
        System.out.println("Personas encontradas: "+personas);
        return personas;
    }
    
    @GET
    @Produces(value=MediaType.APPLICATION_JSON)
    @Path("(id)")//Esto hacer referencia a la siguiente ruta: /personas/(id), ej: /personas/1
    public Persona encontraPersona(@PathParam("id") int id){
        Persona persona = personaDao.encontrarPersona(new Persona(id));
        System.out.println("Persona encontrada: "+persona);
        return persona;
    }
    
    @POST
    @Consumes(value=MediaType.APPLICATION_JSON)
    @Produces(value=MediaType.APPLICATION_JSON)
    public Persona agregarPersona(Persona persona){
        personaDao.insertarPersona(persona);
        System.out.println("Persona agregada: "+persona);
        return persona;
    }
    
    @PUT
    @Consumes(value=MediaType.APPLICATION_JSON)
    @Produces(value=MediaType.APPLICATION_JSON)
    @Path("(id)")
    public Response modificarPersona(@PathParam("id") int id, Persona personaModificada){
        Persona persona = personaDao.encontrarPersona(new Persona(id));
        if(persona != null){
            personaDao.actualizarPersona(personaModificada);
            System.out.println("Persona modificada: "+personaModificada);
            return Response.ok().entity(personaModificada).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Produces(value=MediaType.APPLICATION_JSON)
    @Path("(id)")
    public Response eliminarPersona(@PathParam("id") int id){
        personaDao.eliminarPersona(new Persona(id));
        System.out.println("Persona eliminada con el id: "+id);
        return Response.ok().build();
    }
}
