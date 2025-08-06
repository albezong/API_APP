using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using apiMIRAI_Construcciones.Data;

namespace apiMIRAI_Construcciones.Controllers
{
    public class EmpresasController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Empresas
        public IQueryable<Empresas> GetEmpresas()
        {
            return db.Empresas;
        }

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

        // PUT: api/Empresas/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEmpresas(int id, Empresas empresas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != empresas.idEmpresas)
            {
                return BadRequest();
            }

            db.Entry(empresas).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EmpresasExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Empresas
        [ResponseType(typeof(Empresas))]
        public IHttpActionResult PostEmpresas(Empresas empresas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Empresas.Add(empresas);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = empresas.idEmpresas }, empresas);
        }

        // DELETE: api/Empresas/5
        [ResponseType(typeof(Empresas))]
        public IHttpActionResult DeleteEmpresas(int id)
        {
            Empresas empresas = db.Empresas.Find(id);
            if (empresas == null)
            {
                return NotFound();
            }

            db.Empresas.Remove(empresas);
            db.SaveChanges();

            return Ok(empresas);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EmpresasExists(int id)
        {
            return db.Empresas.Count(e => e.idEmpresas == id) > 0;
        }
    }
}