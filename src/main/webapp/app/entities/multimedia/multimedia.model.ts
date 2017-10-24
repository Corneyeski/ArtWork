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
        public url?: string,
        public urlShare?: string,
        public time?: any,
        public tags?: string,
        public totalValoration?: number,
        public type?: Type,
        public descriptionContentType?: string,
        public description?: any,
        public user?: User,
        public album?: BaseEntity,
    ) {
    }
}
