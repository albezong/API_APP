using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace APIMIRAI_Construcciones.Models
{
    public class QrEquiposDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idQrEquipos { get; set; }
        public string claveQR { get; set; }
        public Nullable<int> idEquipos { get; set; }

        public EquiposDto EquiposDto { get; set; }
    }
}