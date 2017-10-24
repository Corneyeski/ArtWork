import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CommentLike } from './comment-like.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentLikeService {

    private resourceUrl = SERVER_API_URL + 'api/comment-likes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(commentLike: CommentLike): Observable<CommentLike> {
        const copy = this.convert(commentLike);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(commentLike: CommentLike): Observable<CommentLike> {
        const copy = this.convert(commentLike);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CommentLike> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.time = this.dateUtils
            .convertDateTimeFromServer(entity.time);
    }

    private convert(commentLike: CommentLike): CommentLike {
        const copy: CommentLike = Object.assign({}, commentLike);

        copy.time = this.dateUtils.toDate(commentLike.time);
        return copy;
    }
}
