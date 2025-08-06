using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class RevisionesDto
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idRevisiones { get; set; }
        public Nullable<int> idfTiposMantenimientos { get; set; }
        public Nullable<int> idfEquipos { get; set; }
        public Nullable<int> idfUsuarios { get; set; }
        public Nullable<int> idfEmpresas { get; set; }
        public Nullable<System.DateTime> fecha { get; set; }

        public ICollection<DetallesPreventivosDto> DetallesPreventivosDto { get; set; }
        public EmpresasDto EmpresasDto { get; set; }
        public EquiposDto EquiposDto { get; set; }
        public ICollection<RefaccionesDto> RefaccionesDto { get; set; }
        public TiposMantenimientosDto TiposMantenimientosDto { get; set; }
        public UsuariosDto UsuariosDto { get; set; }
    }
}