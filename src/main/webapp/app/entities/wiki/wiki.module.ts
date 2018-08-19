import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSecuritySharedModule } from 'app/shared';
import {
    WikiComponent,
    WikiDetailComponent,
    WikiUpdateComponent,
    WikiDeletePopupComponent,
    WikiDeleteDialogComponent,
    wikiRoute,
    wikiPopupRoute
} from './';

const ENTITY_STATES = [...wikiRoute, ...wikiPopupRoute];

@NgModule({
    imports: [HealthSecuritySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [WikiComponent, WikiDetailComponent, WikiUpdateComponent, WikiDeleteDialogComponent, WikiDeletePopupComponent],
    entryComponents: [WikiComponent, WikiUpdateComponent, WikiDeleteDialogComponent, WikiDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityWikiModule {}
