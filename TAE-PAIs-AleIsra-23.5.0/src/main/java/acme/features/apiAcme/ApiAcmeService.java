
package acme.features.apiAcme;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Anonymous;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Medico;

@Service
public class ApiAcmeService extends AbstractService<Anonymous, Medico> {

	// Internal state ---------------------------------------------------------

	//Se debe de indicar la anotación @Autowired para que Spring se encargue de crear una clase 
	//que implemente el repositorio y a su vez cree un objeto de esa clase y lo inyecte 
	//en las variables definidas.
	@Autowired
	protected ApiAcmeRepository repository;

	// AbstractService interface ----------------------------------------------


	//comprueba si se han recibido los parámetros para procesar la petición
	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	//comprueba si el usuario que ha realizado la petición tiene permisos para poder llevarla a cabo.
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	//se encarga de obtener los objetos de la BD que se van a manejar e introducirlos en el buffer
	@Override
	public void load() {

		Collection<Medico> objects;

		objects = this.repository.findMedicos2();

		super.getBuffer().setData(objects);
	}

	//se encarga de preparar todos los objetos que se van a visualizar en una tupla y de ahí, 
	//se incluyen en la respuesta a la petición. --> **pasa de objeto a texto**
	@Override
	public void unbind(final Medico object) {
		//verifica que el objeto no sea nulo
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "especialidad", "tipoMedico", "userAccount.username", "userAccount.identity.email");

		super.getResponse().setData(tuple);
	}

}
