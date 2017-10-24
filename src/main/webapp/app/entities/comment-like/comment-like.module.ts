import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    CommentLikeService,
    CommentLikePopupService,
    CommentLikeComponent,
    CommentLikeDetailComponent,
    CommentLikeDialogComponent,
    CommentLikePopupComponent,
    CommentLikeDeletePopupComponent,
    CommentLikeDeleteDialogComponent,
    commentLikeRoute,
    commentLikePopupRoute,
} from './';

const ENTITY_STATES = [
    ...commentLikeRoute,
    ...commentLikePopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommentLikeComponent,
        CommentLikeDetailComponent,
        CommentLikeDialogComponent,
        CommentLikeDeleteDialogComponent,
        CommentLikePopupComponent,
        CommentLikeDeletePopupComponent,
    ],
    entryComponents: [
        CommentLikeComponent,
        CommentLikeDialogComponent,
        CommentLikePopupComponent,
        CommentLikeDeleteDialogComponent,
        CommentLikeDeletePopupComponent,
    ],
    providers: [
        CommentLikeService,
        CommentLikePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkCommentLikeModule {}
