import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { UserExt } from './user-ext.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserExtService {

    private resourceUrl = SERVER_API_URL + 'api/user-exts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(userExt: UserExt): Observable<UserExt> {
        const copy = this.convert(userExt);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(userExt: UserExt): Observable<UserExt> {
        const copy = this.convert(userExt);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<UserExt> {
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
        entity.birthdate = this.dateUtils
            .convertLocalDateFromServer(entity.birthdate);
    }

    private convert(userExt: UserExt): UserExt {
        const copy: UserExt = Object.assign({}, userExt);
        copy.birthdate = this.dateUtils
            .convertLocalDateToServer(userExt.birthdate);
        return copy;
    }
}
