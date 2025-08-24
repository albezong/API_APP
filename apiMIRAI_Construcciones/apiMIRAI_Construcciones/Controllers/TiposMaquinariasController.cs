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
    public class TiposMaquinariasController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/TiposMaquinarias
        public IHttpActionResult GetTiposMaquinarias()
        {
            var empresas = db.TiposMaquinarias
                .Select(e => new TiposMaquinariasDto
                {
                    idTiposMaquinarias = e.idTiposMaquinarias,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/TiposMaquinarias/5
        [ResponseType(typeof(TiposMaquinarias))]
        public IHttpActionResult GetTiposMaquinarias(int id)
        {
            var empresa = db.TiposMaquinarias
        .Where(e => e.idTiposMaquinarias == id)
        .Select(e => new TiposMaquinariasDto
        {
            idTiposMaquinarias = e.idTiposMaquinarias,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/TiposMaquinarias/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutTiposMaquinarias(int id, TiposMaquinarias tiposMaquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tiposMaquinarias.idTiposMaquinarias)
            {
                return BadRequest();
            }

            db.Entry(tiposMaquinarias).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TiposMaquinariasExists(id))
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

        // POST: api/TiposMaquinarias
        [ResponseType(typeof(TiposMaquinarias))]
        public IHttpActionResult PostTiposMaquinarias(TiposMaquinarias tiposMaquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.TiposMaquinarias.Add(tiposMaquinarias);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = tiposMaquinarias.idTiposMaquinarias }, tiposMaquinarias);
        }

        // DELETE: api/TiposMaquinarias/5
        [ResponseType(typeof(TiposMaquinarias))]
        public IHttpActionResult DeleteTiposMaquinarias(int id)
        {
            TiposMaquinarias tiposMaquinarias = db.TiposMaquinarias.Find(id);
            if (tiposMaquinarias == null)
            {
                return NotFound();
            }

            db.TiposMaquinarias.Remove(tiposMaquinarias);
            db.SaveChanges();

            return Ok(tiposMaquinarias);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TiposMaquinariasExists(int id)
        {
            return db.TiposMaquinarias.Count(e => e.idTiposMaquinarias == id) > 0;
        }
    }
}