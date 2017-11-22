import { BaseEntity, User } from './../../shared';

export const enum Type {
    'PHOTO',
    'VIDEO',
    'SONG'
}

export class Multimedia implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public imageContentType?: string,
        public image?: any,
        public songContentType?: string,
        public song?: any,
        public url?: string,
        public urlShare?: string,
        public time?: any,
        public tags?: string,
        public totalValoration?: number,
        public type?: Type,
        public description?: any,
        public usersTag?: string,
        public copyright?: string,
        public user?: User,
        public album?: BaseEntity,
    ) {
    }
}
