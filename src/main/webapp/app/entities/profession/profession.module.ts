import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import {
    ProfessionService,
    ProfessionPopupService,
    ProfessionComponent,
    ProfessionDetailComponent,
    ProfessionDialogComponent,
    ProfessionPopupComponent,
    ProfessionDeletePopupComponent,
    ProfessionDeleteDialogComponent,
    professionRoute,
    professionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...professionRoute,
    ...professionPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProfessionComponent,
        ProfessionDetailComponent,
        ProfessionDialogComponent,
        ProfessionDeleteDialogComponent,
        ProfessionPopupComponent,
        ProfessionDeletePopupComponent,
    ],
    entryComponents: [
        ProfessionComponent,
        ProfessionDialogComponent,
        ProfessionPopupComponent,
        ProfessionDeleteDialogComponent,
        ProfessionDeletePopupComponent,
    ],
    providers: [
        ProfessionService,
        ProfessionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkProfessionModule {}
