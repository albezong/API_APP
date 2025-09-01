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
    [RoutePrefix("api/Usuarios")]
    public class UsuariosController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/usuarios
        [HttpGet]
        [Route("")]
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
        //[ResponseType(typeof(Usuarios))]
        [HttpGet]
        [Route("{id:int}")]
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
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutUsuarios(int id, UsuariosDto usuario)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var existingUsuario = db.Usuarios.Find(id);
            if (existingUsuario == null)
                return NotFound();

            if (id != usuario.idUsuarios)
                return BadRequest();

            existingUsuario.nombre = usuario.nombre;
            existingUsuario.apellidoPaterno = usuario.apellidoPaterno;
            existingUsuario.apellidoMaterno = usuario.apellidoMaterno;
            existingUsuario.contraseña = usuario.contraseña;
            existingUsuario.idfRoles = usuario.idfRoles;
            existingUsuario.telefono = usuario.telefono;

            db.SaveChanges();

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
        //[ResponseType(typeof(Usuarios))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostUsuarios(Usuarios usuario)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            db.Usuarios.Add(usuario);
            db.SaveChanges();

            return Ok(usuario);
        }

        // DELETE: api/Usuarios/5
        //[ResponseType(typeof(Usuarios))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteUsuarios(int id)
        {
            var usuario = db.Usuarios.Find(id);
            if (usuario == null)
                return NotFound();

            db.Usuarios.Remove(usuario);
            db.SaveChanges();

            return Ok(usuario);
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