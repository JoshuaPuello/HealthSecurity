import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSecurityWebSharedModule } from 'app/shared';
import {
    TipoRiesgoComponent,
    TipoRiesgoDetailComponent,
    TipoRiesgoUpdateComponent,
    TipoRiesgoDeletePopupComponent,
    TipoRiesgoDeleteDialogComponent,
    tipoRiesgoRoute,
    tipoRiesgoPopupRoute
} from './';

const ENTITY_STATES = [...tipoRiesgoRoute, ...tipoRiesgoPopupRoute];

@NgModule({
    imports: [HealthSecurityWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoRiesgoComponent,
        TipoRiesgoDetailComponent,
        TipoRiesgoUpdateComponent,
        TipoRiesgoDeleteDialogComponent,
        TipoRiesgoDeletePopupComponent
    ],
    entryComponents: [TipoRiesgoComponent, TipoRiesgoUpdateComponent, TipoRiesgoDeleteDialogComponent, TipoRiesgoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityWebTipoRiesgoModule {}
