import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSecuritySharedModule } from 'app/shared';
import {
    RespuestaComponent,
    RespuestaDetailComponent,
    RespuestaUpdateComponent,
    RespuestaDeletePopupComponent,
    RespuestaDeleteDialogComponent,
    respuestaRoute,
    respuestaPopupRoute
} from './';

const ENTITY_STATES = [...respuestaRoute, ...respuestaPopupRoute];

@NgModule({
    imports: [HealthSecuritySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RespuestaComponent,
        RespuestaDetailComponent,
        RespuestaUpdateComponent,
        RespuestaDeleteDialogComponent,
        RespuestaDeletePopupComponent
    ],
    entryComponents: [RespuestaComponent, RespuestaUpdateComponent, RespuestaDeleteDialogComponent, RespuestaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityRespuestaModule {}
