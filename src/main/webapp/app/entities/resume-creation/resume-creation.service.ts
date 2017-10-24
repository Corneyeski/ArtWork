import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ResumeCreation } from './resume-creation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ResumeCreationService {

    private resourceUrl = SERVER_API_URL + 'api/resume-creations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(resumeCreation: ResumeCreation): Observable<ResumeCreation> {
        const copy = this.convert(resumeCreation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(resumeCreation: ResumeCreation): Observable<ResumeCreation> {
        const copy = this.convert(resumeCreation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ResumeCreation> {
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

    private convert(resumeCreation: ResumeCreation): ResumeCreation {
        const copy: ResumeCreation = Object.assign({}, resumeCreation);
        copy.birthdate = this.dateUtils
            .convertLocalDateToServer(resumeCreation.birthdate);
        return copy;
    }
}
