import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HealthSecurityWebEmpleadoModule } from './empleado/empleado.module';
import { HealthSecurityWebReporteModule } from './reporte/reporte.module';
import { HealthSecurityWebRespuestaModule } from './respuesta/respuesta.module';
import { HealthSecurityWebAreaRiesgoModule } from './area-riesgo/area-riesgo.module';
import { HealthSecurityWebTipoRiesgoModule } from './tipo-riesgo/tipo-riesgo.module';
import { HealthSecurityWebTemaModule } from './tema/tema.module';
import { HealthSecurityWebCategoriaModule } from './categoria/categoria.module';
import { HealthSecurityWebWikiModule } from './wiki/wiki.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HealthSecurityWebEmpleadoModule,
        HealthSecurityWebReporteModule,
        HealthSecurityWebRespuestaModule,
        HealthSecurityWebAreaRiesgoModule,
        HealthSecurityWebTipoRiesgoModule,
        HealthSecurityWebTemaModule,
        HealthSecurityWebCategoriaModule,
        HealthSecurityWebWikiModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityWebEntityModule {}
