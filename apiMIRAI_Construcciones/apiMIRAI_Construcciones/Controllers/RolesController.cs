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
    public class RolesController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Roles
        public IHttpActionResult GetRoles()
        {
            var empresas = db.Roles
                .Select(e => new RolesDto
                {
                    idRoles = e.idRoles,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Roles/5
        [ResponseType(typeof(Roles))]
        public IHttpActionResult GetRoles(int id)
        {
            var empresa = db.Roles
        .Where(e => e.idRoles == id)
        .Select(e => new RolesDto
        {
            idRoles = e.idRoles,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Roles/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutRoles(int id, Roles roles)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != roles.idRoles)
            {
                return BadRequest();
            }

            db.Entry(roles).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RolesExists(id))
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

        // POST: api/Roles
        [ResponseType(typeof(Roles))]
        public IHttpActionResult PostRoles(Roles roles)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Roles.Add(roles);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = roles.idRoles }, roles);
        }

        // DELETE: api/Roles/5
        [ResponseType(typeof(Roles))]
        public IHttpActionResult DeleteRoles(int id)
        {
            Roles roles = db.Roles.Find(id);
            if (roles == null)
            {
                return NotFound();
            }

            db.Roles.Remove(roles);
            db.SaveChanges();

            return Ok(roles);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RolesExists(int id)
        {
            return db.Roles.Count(e => e.idRoles == id) > 0;
        }
    }
}