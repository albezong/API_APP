using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class LugaresDto
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idLugares { get; set; }
        public string nombreLugar { get; set; }
        public Nullable<int> idfEquipos { get; set; }

        public EquiposDto EquiposDto { get; set; }
    }
}