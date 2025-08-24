using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Data.Entity;
using System.Web.Services.Description;

namespace APIMIRAI_Construcciones.Controllers
{
    [RoutePrefix("api/historialServicios")]
    public class _2HistorialServiciosController : ApiController
    {
        private readonly PruebaAlmacenTAEPIEntities1 _db= new PruebaAlmacenTAEPIEntities1();

        public class ServicioDto
        {
            public int idRevisiones { get; set; }
            public int? idfTiposMantenimientos { get; set; }
            public string tipoNombre { get; set; }
            public int? idfEquipos { get; set; }
            public string equipoNombre { get; set; }
            public int? idfEmpresas { get; set; }
            public string empresaNombre { get; set; }
            public int? idfUsuarios { get; set; }
            public string usuarioNombre { get; set; }
            public DateTime? fecha { get; set; }

            public int detallesPreventivosCount { get; set; }
            public int refaccionesCount { get; set; }
        }

        // GET: api/Servicios?search=texto&tipo=Preventivo
        [HttpGet, Route("", Name = "GetServicios")]
        public IHttpActionResult GetServicios(string search = null, string tipo = null)
        {
            var q = _db.Revisiones
                       .Include(r => r.TiposMantenimientos)
                       .Include(r => r.Equipos)
                       .Include(r => r.Empresas)
                       .Include(r => r.Usuarios)
                       .Include(r => r.DetallesPreventivos) 
                       .Include(r => r.Refacciones)     
                       .AsQueryable();

            // Filtrar por tipo: si vienen id (número) o nombre ("Preventivo"/"Correctivo")
            if (!string.IsNullOrEmpty(tipo))
            {
                int tipoId;
                if (int.TryParse(tipo, out tipoId))
                {
                    q = q.Where(r => r.idfTiposMantenimientos == tipoId);
                }
                else
                {
                    string tipoLower = tipo.ToLower();
                    q = q.Where(r => r.TiposMantenimientos != null &&
                                     r.TiposMantenimientos.nombre.ToLower().Contains(tipoLower));
                }
            }

            // Búsqueda general en varios campos relacionados
            if (!string.IsNullOrEmpty(search))
            {
                string s = search.ToLower();
                q = q.Where(r =>
                    (r.TiposMantenimientos != null && r.TiposMantenimientos.nombre.ToLower().Contains(s)) ||
                    (r.Equipos != null && (
                        (r.Equipos.nombreArticulo ?? "").ToLower().Contains(s) ||
                        (r.Equipos.nombreComercial ?? "").ToLower().Contains(s) ||
                        (r.Equipos.codigoArticulo ?? "").ToLower().Contains(s) ||
                        (r.Equipos.numIdentificador ?? "").ToLower().Contains(s)
                    )) ||
                    (r.Empresas != null && (r.Empresas.nombre ?? "").ToLower().Contains(s)) ||
                    (r.Usuarios != null && (
                        (r.Usuarios.nombre ?? "").ToLower().Contains(s) ||
                        (r.Usuarios.apellidoPaterno ?? "").ToLower().Contains(s) ||
                        (r.Usuarios.apellidoMaterno ?? "").ToLower().Contains(s)
                    )) ||
                    r.DetallesPreventivos.Any(dp => (dp.comentarios ?? "").ToLower().Contains(s)) || // revisiones1 = DetallesPreventivos nav? (si tu EF la nombró distinto, ajusta)
                    r.Refacciones.Any(re => (re.nombreRefaccion ?? "").ToLower().Contains(s))
                );
            }

            // Proyección a DTO (evita ciclos)
            var list = q.Select(r => new ServicioDto
            {
                idRevisiones = r.idRevisiones,
                idfTiposMantenimientos = r.idfTiposMantenimientos,
                tipoNombre = r.TiposMantenimientos != null ? r.TiposMantenimientos.nombre : null,
                idfEquipos = r.idfEquipos,
                equipoNombre = r.Equipos != null ? r.Equipos.nombreArticulo : null,
                idfEmpresas = r.idfEmpresas,
                empresaNombre = r.Empresas != null ? r.Empresas.nombre : null,
                idfUsuarios = r.idfUsuarios,
                usuarioNombre = r.Usuarios != null ? (r.Usuarios.nombre + " " + (r.Usuarios.apellidoPaterno ?? "")) : null,
                fecha = r.fecha,
                detallesPreventivosCount = r.DetallesPreventivos.Count(), 
                refaccionesCount = r.Refacciones.Count()
            })
            .OrderByDescending(x => x.fecha)
            .ToList();

            return Ok(list);
        }

        // GET: api/Servicios/5
        [HttpGet, Route("{id:int}")]
        [ResponseType(typeof(ServicioDto))]
        public IHttpActionResult GetServicio(int id)
        {
            var r = _db.Revisiones
                       .Include(x => x.TiposMantenimientos)
                       .Include(x => x.Equipos)
                       .Include(x => x.Empresas)
                       .Include(x => x.Usuarios)
                       .FirstOrDefault(x => x.idRevisiones == id);

            if (r == null) return NotFound();

            var dto = new ServicioDto
            {
                idRevisiones = r.idRevisiones,
                idfTiposMantenimientos = r.idfTiposMantenimientos,
                tipoNombre = r.TiposMantenimientos != null ? r.TiposMantenimientos.nombre : null,
                idfEquipos = r.idfEquipos,
                equipoNombre = r.Equipos != null ? r.Equipos.nombreArticulo : null,
                idfEmpresas = r.idfEmpresas,
                empresaNombre = r.Empresas != null ? r.Empresas.nombre : null,
                idfUsuarios = r.idfUsuarios,
                usuarioNombre = r.Usuarios != null ? (r.Usuarios.nombre + " " + (r.Usuarios.apellidoPaterno ?? "")) : null,
                fecha = r.fecha,
                detallesPreventivosCount = r.DetallesPreventivos.Count(),
                refaccionesCount = r.Refacciones.Count()
            };

            return Ok(dto);
        }

        // POST: api/Servicios
        [HttpPost, Route("")]
        [ResponseType(typeof(Revisiones))]
        public IHttpActionResult PostServicio(Revisiones revision)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            _db.Revisiones.Add(revision);
            _db.SaveChanges();

            return CreatedAtRoute("GetServicios", new { id = revision.idRevisiones }, revision);
        }

        // PUT: api/Servicios/5
        [HttpPut, Route("{id:int}")]
        public IHttpActionResult PutServicio(int id, Revisiones revision)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);
            if (id != revision.idRevisiones) return BadRequest();

            var exist = _db.Revisiones.Find(id);
            if (exist == null) return NotFound();

            // Actualizar campos permitidos
            exist.idfTiposMantenimientos = revision.idfTiposMantenimientos;
            exist.idfEquipos = revision.idfEquipos;
            exist.idfUsuarios = revision.idfUsuarios;
            exist.idfEmpresas = revision.idfEmpresas;
            exist.fecha = revision.fecha;

            _db.SaveChanges();
            return Ok(exist);
        }

        // DELETE: api/Servicios/5
        [HttpDelete, Route("{id:int}")]
        public IHttpActionResult DeleteServicio(int id)
        {
            var exist = _db.Revisiones.Find(id);
            if (exist == null) return NotFound();

            _db.Revisiones.Remove(exist);
            _db.SaveChanges();
            return Ok();
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                _db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}