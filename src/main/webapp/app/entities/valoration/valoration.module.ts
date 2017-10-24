import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    ValorationService,
    ValorationPopupService,
    ValorationComponent,
    ValorationDetailComponent,
    ValorationDialogComponent,
    ValorationPopupComponent,
    ValorationDeletePopupComponent,
    ValorationDeleteDialogComponent,
    valorationRoute,
    valorationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...valorationRoute,
    ...valorationPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ValorationComponent,
        ValorationDetailComponent,
        ValorationDialogComponent,
        ValorationDeleteDialogComponent,
        ValorationPopupComponent,
        ValorationDeletePopupComponent,
    ],
    entryComponents: [
        ValorationComponent,
        ValorationDialogComponent,
        ValorationPopupComponent,
        ValorationDeleteDialogComponent,
        ValorationDeletePopupComponent,
    ],
    providers: [
        ValorationService,
        ValorationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkValorationModule {}
