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
    public class UsuariosController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Usuarios
        public IHttpActionResult GetUsuarios()
        {
            var empresas = db.Usuarios
                .Select(e => new UsuariosDto
                {
                    idUsuarios = e.idUsuarios,
                    nombre = e.nombre,
                    apellidoPaterno = e.apellidoPaterno,
                    apellidoMaterno = e.apellidoMaterno,
                    contraseña = e.contraseña,
                    idfRoles  = e.idfRoles,
                    telefono = e.telefono,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Usuarios/5
        [ResponseType(typeof(Usuarios))]
        public IHttpActionResult GetUsuarios(int id)
        {
            var empresa = db.Usuarios
        .Where(e => e.idUsuarios == id)
        .Select(e => new UsuariosDto
        {
            idUsuarios = e.idUsuarios,
            nombre = e.nombre,
            apellidoPaterno = e.apellidoPaterno,
            apellidoMaterno = e.apellidoMaterno,
            contraseña = e.contraseña,
            idfRoles = e.idfRoles,
            telefono = e.telefono,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Usuarios/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUsuarios(int id, Usuarios usuarios)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != usuarios.idUsuarios)
            {
                return BadRequest();
            }

            db.Entry(usuarios).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuariosExists(id))
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

        // POST: api/Usuarios
        [ResponseType(typeof(Usuarios))]
        public IHttpActionResult PostUsuarios(Usuarios usuarios)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Usuarios.Add(usuarios);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = usuarios.idUsuarios }, usuarios);
        }

        // DELETE: api/Usuarios/5
        [ResponseType(typeof(Usuarios))]
        public IHttpActionResult DeleteUsuarios(int id)
        {
            Usuarios usuarios = db.Usuarios.Find(id);
            if (usuarios == null)
            {
                return NotFound();
            }

            db.Usuarios.Remove(usuarios);
            db.SaveChanges();

            return Ok(usuarios);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UsuariosExists(int id)
        {
            return db.Usuarios.Count(e => e.idUsuarios == id) > 0;
        }
    }
}