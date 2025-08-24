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
using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    public class EmpresasController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Empresas
        public IHttpActionResult GetEmpresas()
        {
            var empresas = db.Empresas
                .Select(e => new EmpresasDto
                {
                    idEmpresas = e.idEmpresas,
                    nombre = e.nombre,
                    reportaOsolicita = e.reportaOsolicita,
                })
                .ToList();

            return Ok(empresas);
        }


        // GET: api/Empresas/5
        [ResponseType(typeof(Empresas))]
        public IHttpActionResult GetEmpresas(int id)
        {
            var empresa = db.Empresas
        .Where(e => e.idEmpresas == id)
        .Select(e => new EmpresasDto
        {
            idEmpresas = e.idEmpresas,
            nombre = e.nombre,
            reportaOsolicita = e.reportaOsolicita,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
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