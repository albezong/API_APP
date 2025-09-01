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
    [RoutePrefix("api/Estatus")]
    public class EstatusController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Estatus
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetEstatus()
        {
            var empresas = db.Estatus
                .Select(e => new EstatusDto
                {
                    idEstatus = e.idEstatus,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Estatus/5
        //[ResponseType(typeof(Estatus))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetEstatus(int id)
        {
            var empresa = db.Estatus
        .Where(e => e.idEstatus == id)
        .Select(e => new EstatusDto
        {
            idEstatus = e.idEstatus,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Estatus/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutEstatus(int id, EstatusDto estatus)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var existingEstatus = db.Estatus.Find(id);
            if (existingEstatus == null)
                return NotFound();

            if (id != estatus.idEstatus)
                return BadRequest();

            existingEstatus.nombre = estatus.nombre;

            db.SaveChanges();

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EstatusExists(id))
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

        // POST: api/Estatus
        //[ResponseType(typeof(Estatus))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostEstatus(Estatus estatus)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            db.Estatus.Add(estatus);
            db.SaveChanges();

            return Ok(estatus);
        }

        // DELETE: api/Estatus/5
        //[ResponseType(typeof(Estatus))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteEstatus(int id)
        {
            var estatus = db.Estatus.Find(id);
            if (estatus == null)
                return NotFound();

            db.Estatus.Remove(estatus);
            db.SaveChanges();

            return Ok(estatus);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EstatusExists(int id)
        {
            return db.Estatus.Count(e => e.idEstatus == id) > 0;
        }
    }
}