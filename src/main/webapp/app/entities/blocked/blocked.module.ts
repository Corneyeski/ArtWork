import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    BlockedService,
    BlockedPopupService,
    BlockedComponent,
    BlockedDetailComponent,
    BlockedDialogComponent,
    BlockedPopupComponent,
    BlockedDeletePopupComponent,
    BlockedDeleteDialogComponent,
    blockedRoute,
    blockedPopupRoute,
} from './';

const ENTITY_STATES = [
    ...blockedRoute,
    ...blockedPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BlockedComponent,
        BlockedDetailComponent,
        BlockedDialogComponent,
        BlockedDeleteDialogComponent,
        BlockedPopupComponent,
        BlockedDeletePopupComponent,
    ],
    entryComponents: [
        BlockedComponent,
        BlockedDialogComponent,
        BlockedPopupComponent,
        BlockedDeleteDialogComponent,
        BlockedDeletePopupComponent,
    ],
    providers: [
        BlockedService,
        BlockedPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkBlockedModule {}
