import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Tool } from './tool.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ToolService {

    private resourceUrl = SERVER_API_URL + 'api/tools';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(tool: Tool): Observable<Tool> {
        const copy = this.convert(tool);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tool: Tool): Observable<Tool> {
        const copy = this.convert(tool);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Tool> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Tool.
     */
    private convertItemFromServer(json: any): Tool {
        const entity: Tool = Object.assign(new Tool(), json);
        entity.time = this.dateUtils
            .convertDateTimeFromServer(json.time);
        return entity;
    }

    /**
     * Convert a Tool to a JSON which can be sent to the server.
     */
    private convert(tool: Tool): Tool {
        const copy: Tool = Object.assign({}, tool);

        copy.time = this.dateUtils.toDate(tool.time);
        return copy;
    }
}
