import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { PricesT } from './prices-t.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PricesTService {

    private resourceUrl = SERVER_API_URL + 'api/prices-ts';

    constructor(private http: Http) { }

    create(pricesT: PricesT): Observable<PricesT> {
        const copy = this.convert(pricesT);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pricesT: PricesT): Observable<PricesT> {
        const copy = this.convert(pricesT);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PricesT> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(pricesT: PricesT): PricesT {
        const copy: PricesT = Object.assign({}, pricesT);
        return copy;
    }
}
