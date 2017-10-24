import { BaseEntity, User } from './../../shared';

export class CommentLike implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public user?: User,
        public comment?: BaseEntity,
    ) {
    }
}
