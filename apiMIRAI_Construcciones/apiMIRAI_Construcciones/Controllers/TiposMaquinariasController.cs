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
    [RoutePrefix("api/TiposMaquinarias")]
    public class TiposMaquinariasController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/TiposMaquinarias
        [HttpGet]
        [Route("")]
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
        //[ResponseType(typeof(TiposMaquinarias))]
        [HttpGet]
        [Route("{id:int}")]
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
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutTiposMaquinarias(int id, TiposMaquinariasDto tiposMaquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var existingTiposMaquinarias = db.TiposMaquinarias.Find(id);
            if (existingTiposMaquinarias == null)
                return NotFound();

            if (id != tiposMaquinarias.idTiposMaquinarias)
            {
                return BadRequest();
            }

            existingTiposMaquinarias.nombre = tiposMaquinarias.nombre;

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
        //[ResponseType(typeof(TiposMaquinarias))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostTiposMaquinarias(TiposMaquinarias tiposMaquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.TiposMaquinarias.Add(tiposMaquinarias);
            db.SaveChanges();

            return Ok(tiposMaquinarias);
        }

        // DELETE: api/TiposMaquinarias/5
        //[ResponseType(typeof(TiposMaquinarias))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteTiposMaquinarias(int id)
        {
            var tiposMaquinarias = db.TiposMaquinarias.Find(id);
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