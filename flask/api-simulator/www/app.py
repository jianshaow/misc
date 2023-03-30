import logging
import os

from jinja2.exceptions import TemplateNotFound

from flask import Flask, make_response, render_template, request

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)

app = Flask(__name__)

suffix_mapping = {'application/json': 'json',
                  'application/xml': 'xml', 'text/plain': 'txt', 'text/html': 'html'}


@app.route('/<api>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def serve(api):
    return mock(None, api)


@app.route('/<group>/<api>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def serve_group(group, api):
    return mock(group, api)


def mock(group, api):
    body, content_type, status = get_resp_body(group, api, request)
    logger.info('request body:\n%s', request.get_data(as_text=True))
    logger.info('return body:\n%s', body)
    response = make_response(body, status)
    response.content_type = content_type
    return response


def get_resp_body(group, api, request, context={}):
    context['group'] = group
    context['api'] = api
    context['external_host'] = os.getenv('EXTERNAL_HOST', request.host)
    context['external_scheme'] = os.getenv('EXTERNAL_SCHEME', request.scheme)

    status = 200
    accept_header = request.accept_mimetypes.to_header()
    content_type = accept_header
    suffix = suffix_mapping.get(accept_header, 'json')
    resp_body_tmpl = '{}/{}.{}.{}'.format(
        get_template_path(group), api, request.method, suffix)
    try:
        resp_body = render_template(
            resp_body_tmpl, request=request, context=context)
    except TemplateNotFound:
        resp_body = '{{"error": "no api mockup \'{}\' in group \'{}\' on \'{}\' with type \'{}\'"}}\n'.format(
            api, group, request.method, accept_header)
        content_type = 'application/json'
        status = 404
    return resp_body, content_type, status


def get_template_path(group):
    if group:
        return group
    else:
        return '__root'


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
