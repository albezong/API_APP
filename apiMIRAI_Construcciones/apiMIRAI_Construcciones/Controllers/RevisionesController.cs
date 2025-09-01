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
    [RoutePrefix("api/Revisiones")]
    public class RevisionesController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Revisiones
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetRevisiones()
        {
            var empresas = db.Revisiones
                .Select(e => new RevisionesDto
                {
                    idRevisiones = e.idRevisiones,
                    idfTiposMantenimientos = e.idfTiposMantenimientos,
                    idfEquipos = e.idfEquipos,
                    idfUsuarios = e.idfUsuarios,
                    idfEmpresas = e.idfEmpresas,
                    fecha = e.fecha,
                    descripcion = e.descripcion,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Revisiones/5
        //[ResponseType(typeof(Revisiones))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetRevisiones(int id)
        {
            var revisiones = db.Revisiones
        .Where(e => e.idRevisiones == id)
        .Select(e => new RevisionesDto
        {
            idRevisiones = e.idRevisiones,
            idfTiposMantenimientos = e.idfTiposMantenimientos,
            idfEquipos = e.idfEquipos,
            idfUsuarios = e.idfUsuarios,
            idfEmpresas = e.idfEmpresas,
            fecha = e.fecha,
            descripcion = e.descripcion,
        })
        .FirstOrDefault();

            if (revisiones == null)
            {
                return NotFound();
            }

            return Ok(revisiones);
        }

        // PUT: api/Revisiones/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutRevisiones(int id, Revisiones revisiones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != revisiones.idRevisiones)
            {
                return BadRequest();
            }

            db.Entry(revisiones).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RevisionesExists(id))
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

        // POST: api/Revisiones
        //[ResponseType(typeof(Revisiones))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostRevisiones(Revisiones revisiones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Revisiones.Add(revisiones);
            db.SaveChanges();

            return Ok(revisiones);
        }

        // DELETE: api/Revisiones/5
        //[ResponseType(typeof(Revisiones))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteRevisiones(int id)
        {
            Revisiones revisiones = db.Revisiones.Find(id);
            if (revisiones == null)
            {
                return NotFound();
            }

            db.Revisiones.Remove(revisiones);
            db.SaveChanges();

            return Ok(revisiones);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RevisionesExists(int id)
        {
            return db.Revisiones.Count(e => e.idRevisiones == id) > 0;
        }
    }
}