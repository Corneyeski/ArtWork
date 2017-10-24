import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    ConnectionService,
    ConnectionPopupService,
    ConnectionComponent,
    ConnectionDetailComponent,
    ConnectionDialogComponent,
    ConnectionPopupComponent,
    ConnectionDeletePopupComponent,
    ConnectionDeleteDialogComponent,
    connectionRoute,
    connectionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...connectionRoute,
    ...connectionPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConnectionComponent,
        ConnectionDetailComponent,
        ConnectionDialogComponent,
        ConnectionDeleteDialogComponent,
        ConnectionPopupComponent,
        ConnectionDeletePopupComponent,
    ],
    entryComponents: [
        ConnectionComponent,
        ConnectionDialogComponent,
        ConnectionPopupComponent,
        ConnectionDeleteDialogComponent,
        ConnectionDeletePopupComponent,
    ],
    providers: [
        ConnectionService,
        ConnectionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkConnectionModule {}
