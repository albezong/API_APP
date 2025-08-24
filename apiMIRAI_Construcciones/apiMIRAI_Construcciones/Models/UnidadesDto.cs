using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class UnidadesDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idUnidades { get; set; }
        public string nombre { get; set; }

        [JsonIgnore]
        public ICollection<Equipos> EquiposDto { get; set; }
        [JsonIgnore]
        public ICollection<Refacciones> RefaccionesDto { get; set; }
    }
}