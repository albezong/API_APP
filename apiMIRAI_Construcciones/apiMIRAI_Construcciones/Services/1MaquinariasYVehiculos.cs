using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http.Description;
using System.Web.Http;

namespace apiMIRAI_Construcciones.Services
{
	public class _1MaquinariasYVehiculos
	{
        // GET: api/Empresas/5
        [ResponseType(typeof(Empresas))]
        public IHttpActionResult GetEmpresas(int id)
        {
            Empresas empresas = db.Empresas.Find(id);
            if (empresas == null)
            {
                return NotFound();
            }

            return Ok(empresas);
        }
    }
}